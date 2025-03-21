from PyQt5.QtWidgets import (QMainWindow, QPushButton, QStackedWidget, QLineEdit, 
                             QComboBox, QSpinBox, QRadioButton, QLabel, QTableWidget,
                             QTableWidgetItem, QHeaderView, QWidget, QVBoxLayout, 
                             QHBoxLayout, QGridLayout, QGroupBox)
from PyQt5.QtGui import QFont
from PyQt5.QtCore import Qt

class NutritionKioskUI(QMainWindow):
    def __init__(self):
        super().__init__()
        self.setWindowTitle("Nutrition Consultation Kiosk")
        self.setGeometry(100, 100, 800, 600)
        
        # Main Layout
        self.central_widget = QWidget()
        self.setCentralWidget(self.central_widget)
        main_layout = QVBoxLayout(self.central_widget)

        # Stack widget to manage multiple screens
        self.stack = QStackedWidget()
        main_layout.addWidget(self.stack)

        # Create Patient Information Screen
        self.patient_info_screen = self.create_patient_info_screen()
        self.stack.addWidget(self.patient_info_screen)

        # Create Doctor Selection Screen
        self.doctor_selection_screen = self.create_doctor_selection_screen()
        self.stack.addWidget(self.doctor_selection_screen)

    def create_patient_info_screen(self):
        """Creates the patient information input screen."""
        screen = QWidget()
        layout = QVBoxLayout(screen)

        # Title Label
        title_label = QLabel("Patient Information and Consultation Details", self)
        title_label.setFont(QFont("Arial", 14, QFont.Bold))
        title_label.setAlignment(Qt.AlignCenter)
        layout.addWidget(title_label)

        # Form layout
        form_layout = QGridLayout()

        # Full Name
        form_layout.addWidget(QLabel("Full Name:"), 0, 0)
        self.name_input = QLineEdit()
        self.name_input.setPlaceholderText("Enter Full Name")
        form_layout.addWidget(self.name_input, 0, 1)

        # Phone Number
        form_layout.addWidget(QLabel("Phone Number:"), 1, 0)
        self.phone_input = QLineEdit()
        self.phone_input.setPlaceholderText("Enter 10-digit Phone Number")
        form_layout.addWidget(self.phone_input, 1, 1)

        # Age
        form_layout.addWidget(QLabel("Age:"), 2, 0)
        self.age_input = QSpinBox()
        self.age_input.setMinimum(1)
        self.age_input.setMaximum(120)
        form_layout.addWidget(self.age_input, 2, 1)

        # Gender GroupBox
        gender_box = QGroupBox("Gender")
        gender_layout = QVBoxLayout()
        self.male_radio = QRadioButton("Male")
        self.female_radio = QRadioButton("Female")
        self.other_radio = QRadioButton("Other")
        gender_layout.addWidget(self.male_radio)
        gender_layout.addWidget(self.female_radio)
        gender_layout.addWidget(self.other_radio)
        gender_box.setLayout(gender_layout)
        form_layout.addWidget(gender_box, 3, 0, 1, 2)

        # Child Question GroupBox
        child_box = QGroupBox("Is Patient a Child? (under 18)")
        child_layout = QVBoxLayout()
        self.yes_child_radio = QRadioButton("Yes")
        self.no_child_radio = QRadioButton("No")
        child_layout.addWidget(self.yes_child_radio)
        child_layout.addWidget(self.no_child_radio)
        child_box.setLayout(child_layout)
        form_layout.addWidget(child_box, 4, 0, 1, 2)

        # Nutrition Concern
        form_layout.addWidget(QLabel("Nutrition Concern:"), 5, 0)
        self.problem_combo = QComboBox()
        self.problem_combo.addItems(["Weight Management", "Diabetes", "Heart Health", "General Consultation"])
        form_layout.addWidget(self.problem_combo, 5, 1)

        layout.addLayout(form_layout)

        # Continue Button
        self.continue_button = QPushButton("Continue to Doctor Selection")
        self.continue_button.setStyleSheet("background-color: #007BFF; color: white; padding: 10px; font-weight: bold;")
        layout.addWidget(self.continue_button)

        return screen

    def create_doctor_selection_screen(self):
        """Creates the doctor selection screen."""
        screen = QWidget()
        layout = QVBoxLayout(screen)

        # Title Label
        title_label = QLabel("Select a Doctor")
        title_label.setFont(QFont("Arial", 14, QFont.Bold))
        title_label.setAlignment(Qt.AlignCenter)
        layout.addWidget(title_label)

        # Instruction Label
        instruction_label = QLabel("Please select a doctor for your consultation:")
        layout.addWidget(instruction_label)

        # Table for doctor selection
        self.doctor_table = QTableWidget()
        self.doctor_table.setColumnCount(3)
        self.doctor_table.setHorizontalHeaderLabels(["Doctor ID", "Name", "Consultation Hours"])
        self.doctor_table.horizontalHeader().setSectionResizeMode(QHeaderView.Stretch)

        # Add doctor data
        doctors = [
            ("D001", "Dr. Smith", "09:00 - 17:00"),
            ("D003", "Dr. Williams", "08:00 - 16:00")
        ]
        self.doctor_table.setRowCount(len(doctors))

        for row, (doctor_id, name, hours) in enumerate(doctors):
            self.doctor_table.setItem(row, 0, QTableWidgetItem(doctor_id))
            self.doctor_table.setItem(row, 1, QTableWidgetItem(name))
            self.doctor_table.setItem(row, 2, QTableWidgetItem(hours))

        layout.addWidget(self.doctor_table)

        # Navigation Buttons
        button_layout = QHBoxLayout()

        self.back_button = QPushButton("Back")
        self.back_button.setStyleSheet("background-color: #007BFF; color: white; padding: 10px; font-weight: bold;")
        button_layout.addWidget(self.back_button)

        self.continue_button_doctor = QPushButton("Continue")
        self.continue_button_doctor.setStyleSheet("background-color: #007BFF; color: white; padding: 10px; font-weight: bold;")
        button_layout.addWidget(self.continue_button_doctor)

        layout.addLayout(button_layout)

        return screen