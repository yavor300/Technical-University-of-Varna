import pygame
import pandas as pd
import xgboost as xgb

# Load pre-trained model (ensure you have trained and saved the model as 'xgboost_model.json')
model = xgb.XGBRegressor()
model.load_model("xgboost_model.json")

# Pygame Initialization
pygame.init()

# Screen dimensions and colors
WIDTH, HEIGHT = 800, 600
WHITE, BLACK, GRAY, BLUE = (255, 255, 255), (0, 0, 0), (200, 200, 200), (0, 0, 255)

# Create the screen
screen = pygame.display.set_mode((WIDTH, HEIGHT))
pygame.display.set_caption("eBike Battery Usage Predictor")

# Fonts
font = pygame.font.Font(None, 36)

# Input box properties
input_boxes = {
    "Distance (km)": {"rect": pygame.Rect(50, 50, 200, 40), "text": ""},
    "Elevation Gain (m)": {"rect": pygame.Rect(50, 120, 200, 40), "text": ""},
    "System Weight (kg)": {"rect": pygame.Rect(50, 190, 200, 40), "text": ""},
    "Avg Speed (km/h)": {"rect": pygame.Rect(50, 260, 200, 40), "text": ""},
    "Temperature (°C)": {"rect": pygame.Rect(50, 330, 200, 40), "text": ""},
}
active_box = None
predict_button = pygame.Rect(50, 400, 200, 50)
predicted_result = None

# Main loop
running = True
while running:
    screen.fill(WHITE)

    # Event handling
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False
        elif event.type == pygame.MOUSEBUTTONDOWN:
            if predict_button.collidepoint(event.pos):
                # Predict when the button is clicked
                try:
                    features = {key: float(value["text"]) for key, value in input_boxes.items()}
                    test_data = pd.DataFrame([features])
                    predicted_result = model.predict(test_data)[0]
                except ValueError:
                    predicted_result = "Invalid Input"
            for key, box in input_boxes.items():
                if box["rect"].collidepoint(event.pos):
                    active_box = key
                    break
            else:
                active_box = None
        elif event.type == pygame.KEYDOWN and active_box:
            if event.key == pygame.K_BACKSPACE:
                input_boxes[active_box]["text"] = input_boxes[active_box]["text"][:-1]
            else:
                input_boxes[active_box]["text"] += event.unicode

    # Draw input boxes
    for key, box in input_boxes.items():
        color = BLUE if active_box == key else GRAY
        pygame.draw.rect(screen, color, box["rect"], 2)
        text_surface = font.render(box["text"], True, BLACK)
        screen.blit(text_surface, (box["rect"].x + 5, box["rect"].y + 5))

    # Draw labels
    for i, key in enumerate(input_boxes.keys()):
        label_surface = font.render(key, True, BLACK)
        screen.blit(label_surface, (50, 50 + i * 70 - 30))

    # Draw predict button
    pygame.draw.rect(screen, GRAY, predict_button)
    predict_text = font.render("Predict", True, BLACK)
    screen.blit(predict_text, (predict_button.x + 50, predict_button.y + 10))

    # Display prediction result
    if predicted_result is not None:
        result_text = (
            f"Predicted Battery Used: {predicted_result:.2f}%" if isinstance(predicted_result, float)
            else str(predicted_result)  # Convert non-float result to string
        )
        result_surface = font.render(result_text, True, BLACK)
        screen.blit(result_surface, (300, 450))

    pygame.display.flip()

pygame.quit()
