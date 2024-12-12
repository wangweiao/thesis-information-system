from fastapi import FastAPI, HTTPException
from schemas import ThesisCreate, ThesisResponse
from database import db
from bson.objectid import ObjectId

app = FastAPI()

# uvicorn main:app --reload

@app.post("/theses/", response_model=ThesisResponse, status_code=201)
async def create_thesis(thesis: ThesisCreate):
    result = await db.theses.insert_one(thesis.dict())
    created_thesis = await db.theses.find_one({"_id": result.inserted_id})
    return {"id": str(created_thesis["_id"]), **thesis.dict()}

@app.get("/theses/{thesis_id}", response_model=ThesisResponse)
async def get_thesis(thesis_id: str):
    thesis = await db.theses.find_one({"_id": ObjectId(thesis_id)})
    if not thesis:
        raise HTTPException(status_code=404, detail="Thesis not found")
    thesis["_id"] = str(thesis["_id"])  # Convert ObjectId to string
    return {"id": thesis["_id"], **thesis}

@app.get("/theses/", response_model=list[ThesisResponse])
async def list_theses():
    theses = await db.theses.find().to_list(100)
    for thesis in theses:
        thesis["_id"] = str(thesis["_id"])  # Convert ObjectId to string
    return [{"id": thesis["_id"], **thesis} for thesis in theses]

@app.delete("/theses/{thesis_id}", status_code=204)
async def delete_thesis(thesis_id: str):
    result = await db.theses.delete_one({"_id": ObjectId(thesis_id)})
    if result.deleted_count == 0:
        raise HTTPException(status_code=404, detail="Thesis not found")
    return {"message": "Thesis deleted successfully"}

@app.post("/theses/{thesis_id}", response_model=ThesisResponse)
async def update_student_name(thesis_id: str, student_name: str):
    result = await db.theses.update_one(
        {"_id": ObjectId(thesis_id)},
        {"$set": {"student_name": student_name}}
    )
    if result.matched_count == 0:
        raise HTTPException(status_code=404, detail="Thesis not found")
    
    updated_thesis = await db.theses.find_one({"_id": ObjectId(thesis_id)})
    updated_thesis["_id"] = str(updated_thesis["_id"])  
    return {"id": updated_thesis["_id"], **updated_thesis}
