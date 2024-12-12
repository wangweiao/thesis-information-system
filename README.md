# Smart Thesis Information System

This repository hosts the source code and documentation for the **Smart Thesis Information System**, a proof of concept designed to enhance the academic thesis administration process using cutting-edge technologies, including a large language model.

## Overview

The Smart Thesis Information System automates and streamlines various aspects of thesis management, offering features like real-time feedback, and seamless communication between students, supervisors, and administrators. The system integrates multiple technologies to deliver a holistic and efficient solution.

## Architecture

The system is composed of the following components:

### 1. Authentication Microservice

- **Framework**: Spring Boot
- **Database**: PostgreSQL for persistent storage and Redis for caching
- **Functionality**: Manages user authentication and authorization.

### 2. Core Functionalities Microservice

- **Framework**: FastAPI
- **Database**: MongoDB
- **Functionality**: Handles core thesis administration features

### 3. Large Language Model Integration

- **Model**: Llama 3.2
- **Functionality**: Provides intelligent suggestions, real-time feedback, and natural language interaction via its API.

### 4. Frontend Application

- **Technology**: JavaFX
- **Functionality**: Serves as the user interface, connecting with other components via APIs.

## Features

- **Streamlined Thesis Topic Selection**: Simplifies the process of selecting and approving thesis topics.
- **Intelligent Suggestions**: Leverages Llama 3.2 for topic refinement and feedback.
- **Multi-role Access**: Supports roles for students, supervisors with secure access control.
