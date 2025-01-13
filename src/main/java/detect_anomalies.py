import numpy as np
import pandas as pd
import tensorflow as tf
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.preprocessing import StandardScaler
from elasticsearch import Elasticsearch
from datetime import datetime, timedelta
import smtplib
from email.mime.text import MIMEText

# Connect to Elasticsearch
es = Elasticsearch("http://localhost:9200")

# Define the Elasticsearch index
index_name = "logs"

# Function to fetch logs from Elasticsearch (last 1 minute)
def fetch_logs_from_es():
    one_minute_ago = datetime.utcnow() - timedelta(minutes=1)
    start_time = one_minute_ago.isoformat()

    query = {
        "query": {
            "range": {
                "timestamp": {
                    "gte": start_time
                }
            }
        }
    }

    response = es.search(index=index_name, body=query)
    logs = [hit["_source"]["log_message"] for hit in response['hits']['hits']]
    return logs

# Preprocessing: TF-IDF Vectorization of Log Messages
def preprocess_logs(logs):
    vectorizer = TfidfVectorizer(max_features=100)
    log_features = vectorizer.fit_transform(logs).toarray()
    scaler = StandardScaler()
    log_features = scaler.fit_transform(log_features)
    return log_features, vectorizer, scaler

# Autoencoder Model for Anomaly Detection
def build_autoencoder(input_dim):
    encoding_dim = 32
    autoencoder = tf.keras.Sequential([
        tf.keras.layers.InputLayer(input_shape=(input_dim,)),
        tf.keras.layers.Dense(64, activation='relu'),
        tf.keras.layers.Dense(encoding_dim, activation='relu'),
        tf.keras.layers.Dense(64, activation='relu'),
        tf.keras.layers.Dense(input_dim, activation='sigmoid')
    ])
    autoencoder.compile(optimizer='adam', loss='mse')
    return autoencoder

# Calculate reconstruction error for anomaly detection
def calculate_reconstruction_error(original, reconstructed):
    return np.mean(np.square(original - reconstructed), axis=1)

# Send email alert if anomaly is detected
def send_email_alert(log_message, error):
    msg = MIMEText(f"ALERT! Anomaly detected in log: '{log_message}' with reconstruction error: {error:.4f}")
    msg['Subject'] = 'Log Anomaly Detected'
    msg['From'] = 'youremail@example.com'
    msg['To'] = 'recipient@example.com'
    
    with smtplib.SMTP('smtp.example.com') as server:
        server.login('youremail@example.com', 'yourpassword')
        server.sendmail('youremail@example.com', 'recipient@example.com', msg.as_string())

# Main function for real-time anomaly detection
def main():
    # Fetch logs from Elasticsearch
    logs = fetch_logs_from_es()

    if not logs:
        print("No logs found in the last minute.")
        return

    # Preprocess logs
    log_features, vectorizer, scaler = preprocess_logs(logs)

    # Build and train the autoencoder (only train if necessary)
    input_dim = log_features.shape[1]
    autoencoder = build_autoencoder(input_dim)
    autoencoder.fit(log_features, log_features, epochs=100, batch_size=32, validation_split=0.2)

    # Predict reconstruction and calculate error
    reconstructed_logs = autoencoder.predict(log_features)
    reconstruction_error = calculate_reconstruction_error(log_features, reconstructed_logs)

    # Set threshold for anomaly detection (e.g., error > 0.1 is anomalous)
    threshold = 0.1

    # Detect and alert anomalies
    for i, error in enumerate(reconstruction_error):
        if error > threshold:
            print(f"ALERT! Anomaly detected in log: '{logs[i]}' (Error: {error:.4f})")
            send_email_alert(logs[i], error)

if __name__ == "__main__":
    main()


#pip install tensorflow elasticsearch scikit-learn smtplib
