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

♦ Так как приложение может работать в многопоточном режиме, написаны тесты, с разным колличеством чисел:
100, 1000, 5000, 10000, 100000. С помощью них приблизительно определил от какого количества чисел оптимальнее использовать
многопоточным режим обработки данных. На моем пк это 100_000, но все зависит от машины, на которой будет запущена данная программа.
* `Коллекция для тестирования task-management-system для Postman.json`

♦ Все конечные точки можно просмотреть в документации Swagger, для этого перейти:
* `http://localhost:8080/swagger-ui.html`