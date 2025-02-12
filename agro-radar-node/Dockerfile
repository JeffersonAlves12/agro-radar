# Etapa 1: Construção da aplicação
FROM node:18-alpine AS build

# Define o diretório de trabalho no contêiner
WORKDIR /app

# Copia o arquivo package.json e package-lock.json (se existir)
COPY package*.json ./

# Instala as dependências
RUN npm install

# Copia o restante do código fonte para o contêiner
COPY . .

# Etapa 2: Criação da imagem final
FROM node:18-alpine

# Define o diretório de trabalho no contêiner
WORKDIR /app

# Copia as dependências instaladas e o código da aplicação
COPY --from=build /app /app

# Expõe a porta onde a aplicação vai rodar
EXPOSE 3000

# Comando para rodar a aplicação Node.js
CMD ["npm", "start"]
