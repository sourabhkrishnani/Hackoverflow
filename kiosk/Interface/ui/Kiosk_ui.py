from PyQt5 import QtWidgets, QtCore 
from PyQt5.QtMultimediaWidgets import QVideoWidget

class Ui_MainWindow(object):
    def setupUi(self, MainWindow):
        MainWindow.setObjectName("MainWindow")
        MainWindow.setGeometry(200, 100, 1133, 731)
        self.centralwidget = QtWidgets.QWidget(MainWindow)
        self.centralwidget.setObjectName("centralwidget")

        # Layout setup
        main_layout = QtWidgets.QHBoxLayout(self.centralwidget)

        # Left-side buttons
        left_layout = QtWidgets.QVBoxLayout()
        self.pushBtnAppoinment = QtWidgets.QPushButton("Book Appointment")
        self.pushBtnDiscuss = QtWidgets.QPushButton("Let's Discuss")
        self.pushBtnMeal = QtWidgets.QPushButton("Meal Suggestions")

        for btn in [self.pushBtnAppoinment, self.pushBtnDiscuss, self.pushBtnMeal]:
            btn.setFixedHeight(40)
            btn.setStyleSheet("background-color: #008CBA; color: white; font-size: 14px; border-radius: 5px;")
            left_layout.addWidget(btn)
            left_layout.addSpacing(15)

        self.video_widget = QVideoWidget(self.centralwidget)  # Use QVideoWidget, NOT QFrame
        self.video_widget.setFixedSize(450, 500)
        self.video_widget.setStyleSheet("border: 2px solid #4B0082; background-color: #f0f0f0; padding: 5px;")

        # Right-side buttons
        right_layout = QtWidgets.QVBoxLayout()
        self.pushBtnNutrition = QtWidgets.QPushButton("Nutritional Values")
        self.pushBtnCalculateBMI = QtWidgets.QPushButton("Calculate BMI")
        self.pushBtnTips = QtWidgets.QPushButton("Common Tips")

        for btn in [self.pushBtnNutrition, self.pushBtnCalculateBMI, self.pushBtnTips]:
            btn.setFixedHeight(40)
            btn.setStyleSheet("background-color: #008CBA; color: white; font-size: 14px; border-radius: 5px;")
            right_layout.addWidget(btn)
            right_layout.addSpacing(15)

        # Add layouts to main layout
        main_layout.addLayout(left_layout, 1)
        main_layout.addWidget(self.video_widget, alignment=QtCore.Qt.AlignCenter)
        main_layout.addLayout(right_layout, 1)

        self.centralwidget.setLayout(main_layout)
        MainWindow.setCentralWidget(self.centralwidget)

        self.retranslateUi(MainWindow)

    def retranslateUi(self, MainWindow):
        _translate = QtCore.QCoreApplication.translate
        MainWindow.setWindowTitle(_translate("MainWindow", "AI Health Kiosk"))

if __name__ == "__main__":
    import sys
    app = QtWidgets.QApplication(sys.argv)
    MainWindow = QtWidgets.QMainWindow()
    ui = Ui_MainWindow()
    ui.setupUi(MainWindow)
    MainWindow.show()
    sys.exit(app.exec_())
