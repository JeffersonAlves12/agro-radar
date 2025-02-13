FROM nginx:latest

# Remove configuração padrão
RUN rm /etc/nginx/conf.d/default.conf

# Copia o arquivo de configuração principal
COPY nginx.conf /etc/nginx/nginx.conf

# Copia as configurações do servidor
COPY conf.d/agro-radar.conf /etc/nginx/conf.d/

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]