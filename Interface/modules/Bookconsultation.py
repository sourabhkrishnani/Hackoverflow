import os
import sys
import json
from PyQt5.QtWidgets import QApplication, QMessageBox
# Get the root directory of the project
sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), "../..")))
from Interface.ui.Bookconsultation_ui import NutritionKioskUI
class NutritionKioskApp(NutritionKioskUI):
    def __init__(self):
        super().__init__()
        self.setup_connections()

    def setup_connections(self):
        """Connects UI signals to their respective slots."""
        # Connect "Continue" button on patient info screen
        self.continue_button.clicked.connect(self.go_to_doctor_selection)

        # Connect "Back" and "Continue" buttons on doctor selection screen
        self.back_button.clicked.connect(self.go_to_patient_info)
        self.continue_button_doctor.clicked.connect(self.save_booking_details)

        # Connect doctor table row click
        self.doctor_table.cellClicked.connect(self.select_doctor)

    def select_doctor(self, row, column):
        """Selects a doctor from the table."""
        doctor_id = self.doctor_table.item(row, 0).text()
        doctor_name = self.doctor_table.item(row, 1).text()
        self.selected_doctor = {"id": doctor_id, "name": doctor_name}
        QMessageBox.information(self, "Doctor Selected", f"You selected {doctor_name} (ID: {doctor_id}).")

    def go_to_doctor_selection(self):
        """Switches to the doctor selection screen."""
        self.stack.setCurrentWidget(self.doctor_selection_screen)

    def go_to_patient_info(self):
        """Switches back to the patient information screen."""
        self.stack.setCurrentWidget(self.patient_info_screen)

    def save_booking_details(self):
        """Saves the booking details to a JSON file."""
        if not hasattr(self, "selected_doctor"):
            QMessageBox.warning(self, "Error", "Please select a doctor.")
            return

        # Collect patient information
        patient_name = self.name_input.text()
        phone_number = self.phone_input.text()
        age = self.age_input.value()
        gender = "Male" if self.male_radio.isChecked() else "Female" if self.female_radio.isChecked() else "Other"
        is_child = "Yes" if self.yes_child_radio.isChecked() else "No"
        nutrition_concern = self.problem_combo.currentText()

        # Create booking details dictionary
        booking_details = {
            "patient_name": patient_name,
            "phone_number": phone_number,
            "age": age,
            "gender": gender,
            "is_child": is_child,
            "nutrition_concern": nutrition_concern,
            "doctor_id": self.selected_doctor["id"],
            "doctor_name": self.selected_doctor["name"]
        }

        # Save to JSON file
        filename = f"{patient_name.replace(' ', '_')}_booking.json"
        with open(filename, "w") as file:
            json.dump(booking_details, file, indent=4)

        QMessageBox.information(self, "Booking Saved", f"Booking details saved to {filename}.")

if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = NutritionKioskApp()
    window.show()
    sys.exit(app.exec_())