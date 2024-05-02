# SAMUCABANK é um sistema bancário simulado desenvolvido para fins educativos, projetado para aprimorar habilidades de programação e explorar conceitos relacionados ao desenvolvimento de sistemas bancários. O projeto abrange funcionalidades como operações, depósitos, cartões, usuários, carteiras (wallets), entre outras.

# Tecnologias Usadas
- Spring Boot: Framework para desenvolvimento do backend.
- Spring JPA: Para interações com o banco de dados usando JPA/Hibernate.
- Docker & Docker Compose: Para criar ambientes consistentes e facilitar o desenvolvimento e a execução do projeto.
- MySQL: Banco de dados relacional para armazenamento de dados.
- Flyway: Para migrações e gerenciamento de versão do banco de dados.
- Swagger: Para documentação interativa da API.
- Nginx: Load balancer para gerenciamento de tráfego.
- Mailtrap: Para testes de envio de e-mails.
- Maven: Para build e gerenciamento de dependências.

# Configuração e Execução

Para configurar e executar o SAMUCABANK, siga estas etapas:

1- Clonar o Repositório:

-> git clone https://github.com/seuusuario/samucabank.git

2- Criar a Imagem do Docker:

Antes de iniciar a aplicação, crie a imagem do Docker a partir do arquivo Dockerfile:

-> docker build -t samucabank-image .

3- Configurar o Ambiente:

Certifique-se de ter Docker e Docker Compose instalados. Consulte a documentação oficial para instruções de instalação.

Iniciar os Serviços com Docker Compose:

Agora, inicie os serviços usando Docker Compose:

-> docker-compose up -d

4- Acessar a Aplicação:

Após a inicialização, a aplicação estará disponível em http://localhost:8080. 

Para documentação da API, use Swagger, disponível em http://localhost:8080/swagger-ui.html.

5- Acessar o Banco de Dados MySQL:

Para acessar diretamente o banco de dados, use o seguinte comando:

-> docker exec -it samucabank_db_1 mysql -u root -p
