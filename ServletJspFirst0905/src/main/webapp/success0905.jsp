<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Submission Successful</title>
  <style>
    html, body {
      height: 100%;
      margin: 0;
    }
    body {
      display: flex;
      justify-content: center;   /* horizontal centering */
      align-items: center;       /* vertical centering */
      font-family: Arial, sans-serif;
    }
    table {
      border-collapse: collapse;
      width: 50%;
      margin: auto;              /* fallback horizontal centering */
    }
    th, td {
      border: 1px solid #ccc;
      padding: 10px;
      text-align: left;
    }
    th {
      background-color: #f2f2f2;
    }
    caption {
      font-size: 1.2em;
      margin-bottom: 10px;
    }
  </style>
</head>
<body>
  <h2>Submission Successful!</h2>
  <table>
    <caption>Employee Details</caption>
    <tr>
      <th>Field</th>
      <th>Value</th>
    </tr>
    <tr>
      <td>Employee Name</td>
      <td>${name}</td>
    </tr>
    <tr>
      <td>Employee ID</td>
      <td>${id}</td>
    </tr>
    <tr>
      <td>Position</td>
      <td>${position}</td>
    </tr>
    <tr>
      <td>Phone Number</td>
      <td>${phone}</td>
    </tr>
  </table>
</body>
</html>
