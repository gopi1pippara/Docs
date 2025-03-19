<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ADA Compliance Issues Demo</title>
    <style>
        /* Poor contrast issue */
        .low-contrast {
            background-color: lightgray;
            color: white;
            font-size: 18px;
        }

        /* Missing focus outline issue */
        .no-focus:focus {
            outline: none;
        }

        /* Non-accessible button */
        .bad-button {
            background-color: blue;
            color: white;
            font-size: 20px;
            border: none;
            padding: 10px;
        }
    </style>
</head>
<body>
    
    <!-- 1. Missing ALT text issue -->
    <img src="example.jpg">

    <!-- 2. Poor contrast issue -->
    <p class="low-contrast">This text has poor contrast and is hard to read.</p>

    <!-- 3. Non-descriptive link issue -->
    <a href="https://example.com">Click here</a>

    <!-- 4. Form Accessibility Issues -->
    <form>
        <!-- Missing label -->
        <input type="text" placeholder="Enter your name">

        <!-- Missing fieldset for related fields -->
        <legend>Personal Information</legend>
        <input type="email" id="email">
        <input type="password" id="password">

        <button type="submit">Submit</button>
    </form>

    <!-- 5. Non-semantic elements for buttons -->
    <div class="bad-button" onclick="alert('Clicked!')">Click Me</div>

    <!-- 6. No keyboard focus indicator -->
    <button class="no-focus">Submit</button>

    <!-- 7. Incorrect Heading Structure -->
    <h3>Welcome to Our Site</h3> <!-- Should be h1 -->
    <h1>Subsection 1</h1> <!-- Improper heading level -->
    <h4>Subsection 2</h4> <!-- Skipping levels -->

    <!-- 8. Using a Table for Layout -->
    <table>
        <tr>
            <td>Welcome to our site</td>
            <td>We offer the best products.</td>
        </tr>
    </table>

</body>
</html>
