import pandas as pd
import xgboost as xgb
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_absolute_error, mean_squared_error
import matplotlib.pyplot as plt

# Load the dataset
data = pd.read_csv("battery_data_500.csv")

# Define features and target
features = ["Distance (km)", "Elevation Gain (m)", "System Weight (kg)", "Avg Speed (km/h)", "Temperature (°C)"]
target = "Battery Used (%)"

X = data[features]
y = data[target]

# Split the dataset into training and testing sets
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Initialize the XGBoost regressor
model = xgb.XGBRegressor(
    objective='reg:squarederror',  # Use squared error for regression
    n_estimators=100,  # Number of trees (boosting rounds)
    learning_rate=0.1,  # Step size shrinkage
    max_depth=5,  # Maximum depth of a tree
    random_state=42  # Seed for reproducibility
)

# Train the model
model.fit(X_train, y_train)

# Evaluate the model
y_pred = model.predict(X_test)
mae = mean_absolute_error(y_test, y_pred)
mse = mean_squared_error(y_test, y_pred)

print("Model Evaluation:")
print(f"Mean Absolute Error (MAE): {mae:.2f}")
print(f"Mean Squared Error (MSE): {mse:.2f}")

# Plot feature importance
xgb.plot_importance(model)
plt.title("Feature Importance")
plt.show()

# Save the model as a JSON file
model.save_model("xgboost_model.json")
print("Model saved as 'xgboost_model.json'")
