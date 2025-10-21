This project aims to develop a web platform dedicated to monitoring and managing employee 
attendnce, addressing the needs of companies seeking to modernize and automate their time
tracking processes. The application incorporates essential features, such as facial recognition 
for recording check-in and check-out times, real-time attendance tracking, and the automatic 
generation of detailed reports and statistics. The interface is designed to be intuitive and 
ergonomic, providing users—whether employees or managers—with a smooth and efficient 
experience. Beyond centralizing attendance information, the platform adopts a user-centered 
approach, enabling the consultation of activity histories, monitoring of working hours, and 
optimization of human resource management, while ensuring data reliability, security, and 
confidentiality. 


## 🚀 Features

- **React 18** - React version with improved rendering and concurrent features
- **Vite** - Lightning-fast build tool and development server
- **Redux Toolkit** - State management with simplified Redux setup
- **TailwindCSS** - Utility-first CSS framework with extensive customization
- **React Router v6** - Declarative routing for React applications
- **Data Visualization** - Integrated D3.js and Recharts for powerful data visualization
- **Form Management** - React Hook Form for efficient form handling
- **Animation** - Framer Motion for smooth UI animations
- **Testing** - Jest and React Testing Library setup

## 📋 Prerequisites

- Node.js (v14.x or higher)
- npm 

## 🛠️ Installation

1. Install dependencies:
   ```bash
   npm install
 
   
2. Start the development server:
   ```bash
   npm start


## 📁 Project Structure for the front end

```
react_app/
├── public/             # Static assets
├── src/
│   ├── components/     # Reusable UI components
│   ├── pages/          # Page components
│   ├── styles/         # Global styles and Tailwind configuration
│   ├── App.jsx         # Main application component
│   ├── Routes.jsx      # Application routes
│   └── index.jsx       # Application entry point
├── .env                # Environment variables
├── index.html          # HTML template
├── package.json        # Project dependencies and scripts
├── tailwind.config.js  # Tailwind CSS configuration
└── vite.config.js      # Vite configuration
```

## Architecture technique 
L’application AutoPointage sur une architecture RH-Employé composée de trois couches 
principales :  
• Couche présentation (Front-end) : développée avec React.js, elle permet aux 
utilisateurs (RH et Employé) d’interagir avec l’application via une interface simple et 
intuitive.  
• Couche logique métier (Backend): assurée par Spring Boot , il gère la logique 
métier et les APIs REST, Flask s’occupe de la reconnaissance faciale via DeepFace et 
MediaPipe. 
• Couche données (Base de données): implémentée avec MySQL via XAMPP, il 
Stocke les utilisateurs, employés, présences et rapports de pointage. 
Cette architecture favorise la séparation des responsabilités, la sécurité des données et une 
meilleure scalabilité de l’application
## 🎨 Styling

This project uses Tailwind CSS for styling. The configuration includes:

- Forms plugin for form styling
- Typography plugin for text styling
- Aspect ratio plugin for responsive elements
- Container queries for component-specific responsive design
- Fluid typography for responsive text
- Animation utilities

## 📱 Responsive Design

The app is built with responsive design using Tailwind CSS breakpoints.
## Realisation:
<img width="1648" height="1056" alt="image" src="https://github.com/user-attachments/assets/6e1e05ce-b48e-4bc8-ada9-ba6a0010d83b" />
<img width="1678" height="1647" alt="image" src="https://github.com/user-attachments/assets/b9c5bffd-4d52-4cd7-af05-df619aae9769" />
<img width="1670" height="1268" alt="image" src="https://github.com/user-attachments/assets/2a707c5f-f7cc-47e7-a12f-9fc18eb693b5" />
<img width="1417" height="828" alt="image" src="https://github.com/user-attachments/assets/df0f4ac6-2a24-47f6-8bd5-6beb3bab75cb" />
<img width="1718" height="730" alt="image" src="https://github.com/user-attachments/assets/04e2341b-c3fa-48c3-85df-25b0a9d14de0" />
<img width="1388" height="695" alt="image" src="https://github.com/user-attachments/assets/93dd247b-c0c9-4c18-a367-64e3a96e75d3" />

