FROM node:18 AS builder
WORKDIR /app

COPY package*.json ./
RUN npm install --production=false  # Força instalação completa para dev

COPY . .
RUN npm run build  # Gera o build otimizado

# Etapa final: Executa apenas o necessário
FROM node:18-slim
WORKDIR /app
COPY --from=builder /app/.next ./.next
COPY --from=builder /app/node_modules ./node_modules
COPY --from=builder /app/package.json ./package.json
COPY --from=builder /app/public ./public

EXPOSE 3000
CMD ["npm", "start"]  # Usa o build otimizado