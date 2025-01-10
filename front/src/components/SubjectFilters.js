import React, { useState, useEffect } from 'react';
import '../styles/subject_filters_style.css'

function SubjectFilters({ setLessons }) {
    const [allSubjects, setAllSubjects] = useState([])
    const [whatChosen, setChosen] = useState(-1)

    useEffect(() => {
        fetchSubjects()
    }, [])

    function fetchSubjects() {
        fetch('http://localhost:18018/subjects', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        })
            .then(response => response.json())
            .then(data => {
                setAllSubjects(data)
            })
    }

    function fetchLessonsBySubjects(subject) {
        fetch('http://localhost:18018/lessons/with_subject/' + subject.id, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        })
            .then(response => response.json())
            .then(data => {
                setLessons(data)
            })
    }

    return (
        <div className='filters'>
            {
                allSubjects.map((subject, index) => {
                    return (
                        <div className={'filter ' + (whatChosen == index ? 'chosen' : '')} onClick={() => {
                            fetchLessonsBySubjects(subject)
                            setChosen(index)
                        }}>
                            {subject.name}
                        </div>
                    )
                })
            }
        </div>
    );
};

export default SubjectFilters;
