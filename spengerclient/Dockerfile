FROM node:lts AS buildst

WORKDIR /builddir

COPY . .

RUN npm ci
RUN npm run build

FROM nginx:alpine AS deployment

COPY --from=buildst /builddir/dist/spengerclient/browser /usr/share/nginx/html
COPY --from=buildst /builddir/nginx_config/nginx.conf /etc/nginx

EXPOSE 80

