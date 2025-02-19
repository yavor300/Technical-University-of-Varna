import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_absolute_error, mean_squared_error
import xgboost as xgb
import matplotlib.pyplot as plt

# Load dataset (ensure it includes a "Temperature (°C)" column)
data = pd.read_csv("battery_data_500.csv")

# Define features and target
features = ["Distance (km)", "Elevation Gain (m)", "System Weight (kg)", "Avg Speed (km/h)", "Temperature (°C)"]
target = "Battery Used (%)"

X = data[features]
y = data[target]

# Split into training and testing sets
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Initialize and train the XGBoost model
model = xgb.XGBRegressor(objective='reg:squarederror', n_estimators=100, learning_rate=0.1, max_depth=5, random_state=42)
model.fit(X_train, y_train)

# Make predictions
y_pred = model.predict(X_test)

# Evaluate the model
mae = mean_absolute_error(y_test, y_pred)
mse = mean_squared_error(y_test, y_pred)
print("Model Evaluation:")
print(f"Mean Absolute Error (MAE): {mae:.2f}")
print(f"Mean Squared Error (MSE): {mse:.2f}")

# Plot feature importance
xgb.plot_importance(model)
plt.title("Feature Importance")
plt.show()

# Test prediction with new data, including temperature
test_data = pd.DataFrame({
    "Distance (km)": [30],
    "Elevation Gain (m)": [600],
    "System Weight (kg)": [105],
    "Avg Speed (km/h)": [20],
    "Temperature (°C)": [25]
})

predicted_battery = model.predict(test_data)
print(f"Predicted Battery Used: {predicted_battery[0]:.2f}%")
