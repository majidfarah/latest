const backendBaseUrl = 'http://localhost:8080/api/v1';

async function fetchJson(url, options) {
  const response = await fetch(url, options);
  if (!response.ok) {
    const message = await response.text();
    throw new Error(message || `Request failed with status ${response.status}`);
  }
  return response.json();
}

async function loadSubjects() {
  try {
    const subjects = await fetchJson(`${backendBaseUrl}/subjects`);
    const listEl = document.getElementById('subjects');
    const selectEl = document.getElementById('subject-select');
    listEl.innerHTML = '';
    selectEl.innerHTML = '';
    subjects.forEach((subject) => {
      const li = document.createElement('li');
      li.innerHTML = `<strong>${subject.name}</strong><br/><span>${subject.description}</span>`;
      listEl.appendChild(li);

      const option = document.createElement('option');
      option.value = subject.id;
      option.textContent = subject.name;
      selectEl.appendChild(option);
    });

    if (subjects.length > 0) {
      loadResources(subjects[0].id);
    }
  } catch (error) {
    console.error('Failed to load subjects', error);
  }
}

async function loadResources(subjectId) {
  try {
    const resources = await fetchJson(`${backendBaseUrl}/resources/subject/${subjectId}`);
    const container = document.getElementById('resources');
    container.innerHTML = '';
    resources.forEach((resource) => {
      const card = document.createElement('div');
      card.className = 'resource-card';
      card.innerHTML = `
        <h3>${resource.title}</h3>
        <p><strong>Provider:</strong> ${resource.provider}</p>
        <p><strong>Difficulty:</strong> ${resource.difficulty}</p>
        <p>${resource.description}</p>
        <a href="${resource.url}" target="_blank" rel="noopener">Visit resource</a>
      `;
      container.appendChild(card);
    });
  } catch (error) {
    console.error('Failed to load resources', error);
  }
}

async function requestRecommendation(event) {
  event.preventDefault();
  const subjectSelect = document.getElementById('subject-select');
  const levelSelect = document.getElementById('level-select');
  const goalsInput = document.getElementById('goals-input');
  const recommendationContainer = document.getElementById('recommendation');

  const goals = goalsInput.value
    .split(',')
    .map((goal) => goal.trim())
    .filter(Boolean)
    .map((topic, index) => ({ topic, priority: index + 1 }));

  try {
    const recommendation = await fetchJson(`${backendBaseUrl}/recommendations`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        subject: subjectSelect.options[subjectSelect.selectedIndex].text,
        level: levelSelect.value,
        goals,
      }),
    });

    recommendationContainer.innerHTML = `
      <h3>${recommendation.subject} – ${recommendation.level}</h3>
      <p>${recommendation.overview}</p>
      <h4>Milestones</h4>
      <ul>${recommendation.milestones.map((milestone) => `<li>${milestone}</li>`).join('')}</ul>
      <h4>Recommended Resources</h4>
      <ul>${recommendation.recommendedResources
        .map(
          (resource) => `<li><a href="${resource.url}" target="_blank" rel="noopener">${resource.title}</a> — ${resource.provider}</li>`
        )
        .join('')}</ul>
    `;

    loadResources(subjectSelect.value);
  } catch (error) {
    recommendationContainer.innerHTML = `<p class="error">Unable to generate recommendations: ${error.message}</p>`;
  }
}

document.getElementById('recommendation-form').addEventListener('submit', requestRecommendation);
document.getElementById('subject-select').addEventListener('change', (event) => {
  loadResources(event.target.value);
});

loadSubjects();
