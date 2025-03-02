echo "# java-tasks" >> README.md \
git init \
git add README.md \
git commit -m "first commit" \
git branch -M main \
git remote add origin https://github.com/MotaMota0/java-tasks.git \
git push -u origin main 

# Task1

## 📌 Описание
Этот проект предназначен для работы с PDF-файлами. Он позволяет создавать, редактировать и извлекать данные из PDF с помощью iText и Apache PDFBox.

## 🛠 Технические детали
- **Язык**: Java 21
- **Система сборки**: Apache Maven
- **Формат проекта**: Maven Project (`pom.xml`)
- **Версия Java**: 21 (указывается в `pom.xml`)

  
Проект использует следующие библиотеки: 

📄 iText 5.5.13.3 – для создания PDF \
📄 iText 7.1.15 – для расширенной работы с PDF \
📄 Apache PDFBox 2.0.27 – для анализа и обработки PDF \
📄 Tabula 1.0.4 – для извлечения таблиц из PDF \
Все зависимости автоматически загружаются через Maven.
