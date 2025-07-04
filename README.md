# 📝 Grammar Notes API

A simple Spring Boot API for grammar checking and managing text notes in Markdown and HTML formats. 

---

## 🚀 Endpoints

### ✅ `POST /check-grammar`
Check grammar for the submitted text sent as form-data.

- **Request Parameter**: `request` (string, form-data)  
- **Response**: List of grammar suggestions.


---

### ✅ POST /save-note
Saves the submitted text as a .md Markdown file in the notes/ folder.

- **Request Parameter**: Plain text  
- **Response**: String with UUID saved as file name.

**Example Response:**
Note saved with id: db5ebfac-24b9-482a-a9ad-cf29b22aeebd


---

### ✅ GET /notes
Returns a list of all saved notes with UUIDs

**Example Response:**
[
    "25c39943-f7c6-4c35-86ac-8ea6aa488d46.md",
    "7b513310-355d-4e8a-8566-8e690952c65d.md",
    "ce039196-eaca-4cb8-a073-57259d91ba69.md"
]


---

### ✅ GET /notes/{noteId}
Returns the content of a saved note rendered as HTML.

- **Path Parameter**: noteId (UUID without .md)  
- **Response**: HTML content.

**Example Response:**
html
<h1>My Note</h1>
<p>This is a saved note.</p>


---

## 🛠 Technologies

- Java 21
- Spring Boot
- LanguageTool for Grammar checking
- CommonMark to HTML 
- UUID for file naming

---

## 📁 File Structure

- Notes are saved in the notes/ folder
- Filenames use UUIDs: xxxxxxx.md

---

## ▶️ Running the App

1. Clone the repository:
   
bash
   git clone [https://github.com/your-user/grammar-notes-api.git](https://github.com/Guilherme-Fenrique/markdown-note-app)
   
2. Run with Maven:
   
bash
   ./mvnw spring-boot:run


3. Access the API at:
   
http://localhost:8080


---


## 📄 License

MIT – feel free to use, modify, and share.
