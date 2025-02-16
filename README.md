# Backend Server for AI-Powered BI Platform

A comprehensive AI-driven web platform for business intelligence and data analysis

This platform enables users to upload Excel data sheets, specify their analysis objectives, and receive AI-generated
insights along with chart codes for visualization. By leveraging AI-driven data analysis, the platform provides
structured responses, enhances accuracy, and supports dynamic data visualization.

## Features

- Excel Data Upload: Users can upload Excel files and define their analysis goals.
- AI-Driven Analysis: AI acts as a data analyst, following structured prompts to ensure accurate responses.
- Dynamic Visualization: Supports 5+ types of charts using ECharts V5.
- Rate Limiting: Implemented using Redisson for stability and fairness.
- User-Friendly UI: Built with ReactJS and Ant Design for a seamless experience.
- Data Storage: Analysis results are stored in a MySQL database, allowing users to access past reports.

## Tech Stack

### Frameworks

- Spring Boot 2.7.x
- Spring MVC
- MyBatis + MyBatis Plus

### Database

- MySQL

### Rate Limiter

- Redis
- Redisson 3.44.0

### AI Interface

- SydneyQT

### Tools

- Easy Excel
- Gson
- Apache Commons Lang3
- Lombok

### Unit Tests

- JUnit5

## Usage

- Enter an analysis name and goal.
- Select a chart type.
- Upload an Excel file.
- Wait until receive AI analysis results and chart.
- View past analyses from My Charts.

### Screenshots & Examples

![](/Users/nicklee/Desktop/Screenshot 2025-01-16 at 10.42.35 PM.png)
![](/Users/nicklee/Desktop/Screenshot 2025-01-16 at 10.43.10 PM.png)