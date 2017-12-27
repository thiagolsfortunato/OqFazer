## OqFazer ##

## Tecnologias: ##

- Maven - 3.0.5
- Java 8
- Spring-boot-starter:1.5.7.RELEASE
- Spring-boot-starter-security:1.5.7.RELEASE
- Spring-boot-starter-web:1.5.7.RELEASE
- Spring-boot-starter-test:1.5.7.RELEASE
- Spring-boot-starter-data-jpa:1.5.7.RELEASE
- Spring-boot-starter-websocket:1.5.7.RELEASE
- Spring-boot-starter-thymeleaf:1.5.7.RELEASE
- Jsonwebtoken:jjwt:0.4
- Jackson-databind:2.8.9
- Liquibase-core:3.5.3
- Lombok:1.16.18
- Mysql-connector-java:5.1.6
- Commons-lang:3:3.0
- Reactor-core:2.0.8.RELEASE
- Reactor-net:2.0.8.RELEASE
- Netty-all:4.1.7.Final
- Netty-transport-native-epoll:4.1.7.Final

## Contribuidores

Contribuidores do projeto:
- Thiago Fortunato - thiagolsfortunato@hotmail.com


## Visão Geral

Projeto pessoal que tem a finalidade de prover um local para que Região do Vale do Paraíba possa consultar todos eventos que estão acontecendo na região.
O público alvo deste projeto são as lojas, agencias de evento ou quaisquer usuários que desejam compartilhar um evento à região. 
A API tem como objetivo disponibilizar os serviços para cadastro de eventos, categorias, usuarios, cidades, regiões.

## Instalação

Esta seção destina-se a apresentar as características do ambiente onde o sistema deve ser instalado.

## Desenvolvimento

### Configurando ambiente

- As configurações de conexão ao banco se encontram no arquivo 
`application.properties`. Alterar as seguintes linhas para realizar 
a conexão nos bancos de dados:
```shell
liquibase.url=jdbc:mysql://[IP DO HOST DO BANCO DE DADOS MYSQL]:3306/oqfazer?createDatabaseIfNotExist=true
liquibase.user=[USUARIO DO BANCO DE DADOS]
liquibase.password=[SENHA DO BANCO DE DADOS]
```
```shell
spring.datasource.url=jdbc:mysql://[IP DO HOST DO BANCO DE DADOS MYSQL]:3306/oqfazer?createDatabaseIfNotExist=true
spring.datasource.username=[USUARIO DO BANCO DE DADOS]
spring.datasource.password=[SENHA DO BANCO DE DADOS]
```

- No Intellij basta importar as dependências pela ferramenta ou executar o comando:
```shell
mvn clean package
```

### Executando a aplicação
Pronto, o ambiente já está configurado e apto a execução
 pelo Intellij ou executando o .jar após compilado 
 pelo comando:
```shell
cd oqfazer/
java -jar target/oqfazer-0.0.1-SNAPSHOT.jar
```

### Teste da aplicação
Para testar se API está funcionando realizar login pelo endpoint:
```shell
Método POST
http://[IP DO HOST]/api/auth
Header: "Content-Type", "value":"application/json"
Body: 
{
    "username":"admin",
    "password":"admin"
}
```
