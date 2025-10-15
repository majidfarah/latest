from fastapi import FastAPI
from pydantic import BaseModel, Field
from typing import List, Optional

app = FastAPI(title="STEM Learning Guide AI Service", version="0.1.0")


class LearningGoal(BaseModel):
    topic: str = Field(..., description="Specific concept or topic of interest")
    priority: Optional[int] = Field(None, description="Optional ordering hint")


class RecommendationRequest(BaseModel):
    subject: str
    level: str
    goals: List[LearningGoal] = Field(default_factory=list)


class RecommendationResource(BaseModel):
    title: str
    url: str
    provider: str
    description: str


class RecommendationResponse(BaseModel):
    subject: str
    level: str
    overview: str
    recommended_resources: List[RecommendationResource]
    milestones: List[str]


SUBJECT_LIBRARY = {
    "mathematics": {
        "BEGINNER": [
            RecommendationResource(
                title="Khan Academy Algebra",
                url="https://www.khanacademy.org/math/algebra",
                provider="Khan Academy",
                description="Foundational algebra lessons with interactive practice"
            ),
            RecommendationResource(
                title="3Blue1Brown Essence of Linear Algebra",
                url="https://www.3blue1brown.com/essence-of-linear-algebra",
                provider="3Blue1Brown",
                description="Visual explanations of vectors, transformations, and matrices"
            ),
        ],
        "INTERMEDIATE": [
            RecommendationResource(
                title="MIT OCW Multivariable Calculus",
                url="https://ocw.mit.edu/courses/18-02sc-multivariable-calculus-fall-2010/",
                provider="MIT OpenCourseWare",
                description="University-level calculus with lecture notes and assignments"
            )
        ],
    },
    "artificial intelligence": {
        "BEGINNER": [
            RecommendationResource(
                title="Elements of AI",
                url="https://www.elementsofai.com/",
                provider="University of Helsinki",
                description="Gentle introduction to AI concepts with quizzes and exercises"
            ),
            RecommendationResource(
                title="Google Machine Learning Crash Course",
                url="https://developers.google.com/machine-learning/crash-course",
                provider="Google",
                description="Hands-on ML course with videos and interactive labs"
            ),
        ],
        "INTERMEDIATE": [
            RecommendationResource(
                title="Fast.ai Practical Deep Learning",
                url="https://course.fast.ai/",
                provider="Fast.ai",
                description="Project-based deep learning with notebooks and community"
            ),
            RecommendationResource(
                title="Coursera Machine Learning Specialization",
                url="https://www.coursera.org/specializations/machine-learning-introduction",
                provider="DeepLearning.AI",
                description="Andrew Ng's modernized ML curriculum"
            ),
        ],
    },
}


def build_overview(subject: str, level: str, goals: List[LearningGoal]) -> str:
    goal_summary = ", ".join(goal.topic for goal in goals) or "core fundamentals"
    return (
        f"Personalized {subject.title()} learning path for {level.lower()} learners focusing on {goal_summary}."
    )


def build_milestones(subject: str, level: str, goals: List[LearningGoal]) -> List[str]:
    base = [
        f"Understand foundational concepts in {subject}",
        "Complete recommended resources and take structured notes",
        "Apply knowledge to a mini-project or practice assessment",
    ]
    if goals:
        base.append(f"Deep dive into: {', '.join(goal.topic for goal in goals)}")
    if level.upper() == "INTERMEDIATE":
        base.append("Contribute to an open-source or collaborative study group project")
    return base


@app.post("/api/v1/learning-path", response_model=RecommendationResponse)
def generate_learning_path(request: RecommendationRequest) -> RecommendationResponse:
    subject_key = request.subject.lower()
    level_key = request.level.upper()

    resources = SUBJECT_LIBRARY.get(subject_key, {}).get(level_key)
    if not resources:
        resources = [
            RecommendationResource(
                title="STEM Learning Guide Resource Explorer",
                url="https://example.com/stem-guide",
                provider="STEM Guide",
                description="Curated list of STEM resources tailored to your interests"
            )
        ]

    overview = build_overview(request.subject, request.level, request.goals)
    milestones = build_milestones(request.subject, request.level, request.goals)

    return RecommendationResponse(
        subject=request.subject,
        level=request.level,
        overview=overview,
        recommended_resources=resources,
        milestones=milestones,
    )


@app.post("/api/v1/recommendations", response_model=RecommendationResponse)
def recommend_resources(request: RecommendationRequest) -> RecommendationResponse:
    return generate_learning_path(request)
