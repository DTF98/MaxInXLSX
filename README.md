# MaxInXLSX
Сервис для поиска максимального n-ого числа в файле с разрешением .xlsx.

♦ Project Stack:
* Java 17
* Spring Boot 3.4.2
* Docker
* Lombok
* Open API
* Swagger

♦ Инструкция для развертывания проекта в docker:
* Собрать проект с помощью maven команд:
    * `clean`
    * `install`
* Собрать докер образ и запустить докер контейнер, используя команды:
    * `docker build -t max-in-xlsx . && docker compose up`

♦ Все конечные точки можно просмотреть в документации Swagger, для этого перейти:
* `http://localhost:8080/swagger-ui.html`