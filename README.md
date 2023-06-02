# generatePdf
1. Clone this project 
2. Import this project as Maven Project in your IDE 
3. Install and setup MySQL
   - Create schema and named schematest
   - Create table history equate with HeaderEntity
4. Generate your access token
    - Follow this https://www.geeksforgeeks.org/how-to-generate-personal-access-token-in-github/
5. Setup your project properties like username and password MySQL, Github access token
6. Run the project

# Postman
1.  Github Public API  = curl --location --request GET 'https://api.github.com/search/users?per_page=2&q=Q' \
    --header 'Accept: application/vnd.github+json' \
    --header 'Authorization: Bearer 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' \
    --header 'X-GitHub-Api-Version: 2022-11-28'
2. Generate PDF = curl --location --request GET 'http://localhost:8091/api/main/user/file?perPage=6'
3. History generate PDF = curl --location --request GET 'http://localhost:8091/api/main/history/list-pdf'
4. Download existing PDF by id = curl --location --request GET 'http://localhost:8091/api/main/download?idHistory=12'