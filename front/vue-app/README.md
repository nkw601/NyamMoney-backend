# Financial Dashboard Vue (scaffold)

This folder contains a Vite + Vue 3 + TypeScript + Pinia + Vue Router + Tailwind scaffold for the financial dashboard migration.

Quick start:

```bash
cd vue-app
npm install
npm run dev
```

The project was scaffolded to mirror the original React project structure. Next steps: copy component/page conversions into `src/views` and `src/components`.
# Financial Dashboard — Vue 3 Migration

This folder contains a starter Vue 3 + Vite + TypeScript + Tailwind scaffold extracted from the original Next.js project to begin migrating components incrementally.

Quick start:

```bash
cd vue-app
npm install
npm run dev
```

Notes:

- Components from the original `components/` should be ported to `src/components/` as `.vue` files.
- Routes live in `src/router`.
- Global styles are in `src/assets/global.css` (Tailwind).
