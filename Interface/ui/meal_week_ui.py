import sys
from PyQt5.QtWidgets import (
    QApplication, QWidget, QLabel, QVBoxLayout, QComboBox, QTextEdit, QPushButton, QMessageBox
)
from PyQt5.QtGui import QFont
from PyQt5.QtCore import Qt
from PyQt5.QtSql import QSqlDatabase, QSqlQuery

class MealWeekUI(QWidget):
    def __init__(self):
        super().__init__()
        if self.create_db_connection():
            self.initUI()
            self.populate_user_list()
        else:
            self.show_error_message("Database Connection Error", "Unable to connect to the database.")

    def initUI(self):
        self.setWindowTitle("Personalized Weekly Meal Plan")
        self.setGeometry(200, 100, 800, 600)
        self.setStyleSheet("background-color: #f0f8ff; padding: 15px;")

        layout = QVBoxLayout()
        layout.setSpacing(20)

        header_label = QLabel("🍽 Personalized Meal Plan for the Week")
        header_label.setFont(QFont("Arial", 18, QFont.Bold))
        header_label.setAlignment(Qt.AlignCenter)
        layout.addWidget(header_label)

        self.user_combo = QComboBox()
        self.user_combo.setFont(QFont("Arial", 14))
        self.user_combo.currentIndexChanged.connect(self.display_meal_plan)
        layout.addWidget(self.user_combo)

        self.meal_plan_display = QTextEdit()
        self.meal_plan_display.setFont(QFont("Arial", 12))
        self.meal_plan_display.setReadOnly(True)
        layout.addWidget(self.meal_plan_display)

        refresh_button = QPushButton("Refresh User List")
        refresh_button.setStyleSheet("""
        QPushButton {
            background-color: #008CBA;
            color: white;
            padding: 8px;
            border-radius: 5px;
            font-size: 14px;
            font-weight: bold;
        }
        QPushButton:hover {
            background-color: #005f73;
        }
        """)
        refresh_button.clicked.connect(self.populate_user_list)
        layout.addWidget(refresh_button, alignment=Qt.AlignCenter)

        self.setLayout(layout)

    def create_db_connection(self):
        db = QSqlDatabase.addDatabase('QMYSQL')
        db.setHostName('localhost')
        db.setDatabaseName('meal_planner')
        db.setUserName('your_username')
        db.setPassword('your_password')
        if not db.open():
            print(f"Database Error: {db.lastError().text()}")
            return False
        return True

    def populate_user_list(self):
        query = QSqlQuery("SELECT user_id, name FROM users")
        self.user_combo.clear()
        while query.next():
            user_id = query.value(0)
            name = query.value(1)
            self.user_combo.addItem(name, user_id)

    def display_meal_plan(self):
        user_id = self.user_combo.currentData()
        if user_id is not None:
            query = QSqlQuery()
            query.prepare("SELECT * FROM users WHERE user_id = ?")
            query.addBindValue(user_id)
            if query.exec_() and query.next():
                user_details = [query.value(i) for i in range(query.record().count())]
                meal_plan = self.generate_meal_plan(user_details)
                self.meal_plan_display.setText(meal_plan)
            else:
                self.meal_plan_display.setText("No user details found.")

    def generate_meal_plan(self, user_details):
        name, age, bmi, allergies = user_details[1], user_details[2], user_details[3], user_details[4]
        query = QSqlQuery()
        query.prepare("SELECT meal_plan FROM meal_suggestions WHERE bmi_category = ? AND allergies = ?")
        query.addBindValue(bmi)
        query.addBindValue(allergies)
        if query.exec_() and query.next():
            meal_plan = query.value(0)
            return f"Hello {name},\n\nHere is your personalized meal plan for the week:\n\n{meal_plan}"
        else:
            return "No meal plan found for your profile."

    def show_error_message(self, title, message):
        msg_box = QMessageBox()
        msg_box.setIcon(QMessageBox.Critical)
        msg_box.setWindowTitle(title)
        msg_box.setText(message)
        msg_box.exec_()

    def closeEvent(self, event):
        QSqlDatabase.removeDatabase('QMYSQL')
        event.accept()

if __name__ == '__main__':
    app = QApplication(sys.argv)
    ex = MealWeekUI()
    ex.show()
    sys.exit(app.exec_())
