# 📚 Gestão de Processos Judiciais - Spring Boot API

Esta API permite gerenciar processos judiciais e agendar audiências com autenticação JWT.

---

## 🚀 Como rodar a aplicação localmente

### ✅ Pré-requisitos

- Java 17 ou superior
- Maven
- Eclipse IDE com suporte a Spring (ou Spring Tools Suite)

---

### ▶️ Rodando no Eclipse

1. **Importar o projeto**

   - Vá em: `File` > `Import` > `Existing Maven Projects`
   - Selecione a pasta `processo-api`
   - Clique em `Finish`

2. **Atualizar dependências Maven (se necessário)**

   - Clique com o botão direito no projeto → `Maven` → `Update Project` (ou use `Alt+F5`)

3. **Executar a aplicação**

   - Navegue até a classe `ProcessosApiApplication.java`
   - Clique com o botão direito → `Run As` → `Java Application`

---

### ▶️ Rodando via terminal

#bash
git clone https://github.com/WellersonAlex/processos-api.git
cd processos-api
./mvnw spring-boot:run
