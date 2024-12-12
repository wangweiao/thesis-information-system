# # app/database.py
# from motor.motor_asyncio import AsyncIOMotorClient

# # MongoDB connection string (Replace with your credentials or URI)
# MONGO_URL = "mongodb://localhost:27017"

# client = AsyncIOMotorClient(MONGO_URL)
# database = client["fastapi_database"]  # Database name
# collection = database["items"]  # Collection name


from motor.motor_asyncio import AsyncIOMotorClient

# MongoDB connection string
MONGO_URI = "mongodb://localhost:27017"

client = AsyncIOMotorClient(MONGO_URI)
db = client.thesis_database
