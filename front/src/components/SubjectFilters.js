import React, { useState, useEffect } from 'react';
import '../styles/subject_filters_style.css'

function SubjectFilters() {
    const [allSubjects, setAllSubjects] = useState([])

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

    return (
        <div className='filters'>
            {
                allSubjects.map(subject => {
                    return(
                        <div className='filter'>
                            {subject.name}
                        </div>
                    )
                })
            }
        </div>
    );
};

export default SubjectFilters;
