# YumMoney Frontend

A cute lucky cat-themed personal finance web app built with Vue 3, TypeScript, and Vite.

## Tech Stack

- Vue 3 (Composition API)
- TypeScript
- Vue Router
- Pinia (State Management)
- Axios (HTTP Client)
- SCSS (Styling)
- Vite (Build Tool)

## Design System

Inspired by the adorable maneki-neko (lucky cat) logo:
- Cream (#F5E6D3) - Base color
- Golden (#FFD54F) - Accent
- Coral (#FF8A65) - Highlights
- Warm Brown (#5C4033) - Text & borders

## Setup

1. Install dependencies:
\`\`\`bash
npm install
\`\`\`

2. Create `.env` file:
\`\`\`env
VITE_API_BASE_URL=http://localhost:8080/api
\`\`\`

3. Start development server:
\`\`\`bash
npm run dev
\`\`\`

The app will run at http://localhost:3000

## Backend Requirements

Make sure your Spring Boot backend is running on http://localhost:8080 with these endpoints:
- POST /api/auth/login
- POST /api/auth/signup
- POST /api/auth/refresh
- GET /api/transactions
- GET /api/challenges
- GET /api/posts

## Build for Production

\`\`\`bash
npm run build
\`\`\`

Preview production build:
\`\`\`bash
npm run preview
\`\`\`

## Project Structure

\`\`\`
src/
├── components/     # Reusable components
├── views/          # Page components
├── stores/         # Pinia stores
├── services/       # API services
├── router/         # Vue Router config
├── styles/         # SCSS files
├── types/          # TypeScript types
└── utils/          # Helper functions
