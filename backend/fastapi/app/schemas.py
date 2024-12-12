from pydantic import BaseModel, Field
from typing import Optional

# Schema for creating a new thesis
class ThesisCreate(BaseModel):
    title: str = Field(..., example="Deep Learning for NLP")
    description: str = Field(..., example="Exploring the use of deep learning in NLP tasks.")
    supervisor_name: str = Field(..., example="Dr. Smith")
    student_name: str = Field(..., example="John Doe")
    year: int = Field(..., example=2024)

# Schema for returning thesis data (response)
class ThesisResponse(BaseModel):
    id: str
    title: str
    description: str
    supervisor_name: str
    student_name: str
    year: int

    class Config:
        orm_mode = True
