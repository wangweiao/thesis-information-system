from pydantic import BaseModel, Field
from typing import Optional

class Thesis(BaseModel):
    title: str = Field(..., example="Deep Learning for NLP")
    description: str = Field(..., example="Exploring the use of deep learning in NLP tasks.")
    supervisor_name: str = Field(..., example="Dr. Smith")
    student_name: str = Field(..., example="John Doe")
    year: int = Field(..., example=2024)
