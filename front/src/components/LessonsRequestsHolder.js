import React, { useEffect, useState } from 'react';
import '../styles/lessons_requests_style.css'
import { getCurrentUserEmail } from '../App';
import { YesIcon, NoIcon } from './modals/InfoLessonModal';

function LessonsRequestsHolder({ openLessonInfo }) {
    const [income, setIncome] = useState([])
    const [outcome, setOutcome] = useState([])

    useEffect(() => {
        fetchOutcomeRequests()
        fetchIncomeRequests()
    }, [])

    function fetchOutcomeRequests() {
        fetch("http://localhost:18018/lesson_requests/outcome/with_user/" + getCurrentUserEmail(), {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        })
            .then(response => response.json())
            .then(data => {
                setOutcome(data)
            })
    }

    function fetchIncomeRequests() {
        fetch("http://localhost:18018/lesson_requests/income/with_user/" + getCurrentUserEmail(), {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        })
            .then(response => response.json())
            .then(data => {
                console.log(data)
                setIncome(data)
            })
    }

    return (
        <div className='lessons-requests-holder'>
            <div className='lessons-requests-content'>
                <div className='requests-buttons'>
                    <button>Income Requests</button>
                    <button>Outcome Requests</button>
                </div>
                <div>
                    {income.map(request => {
                        return (
                            <div className='card'>
                                <h4 className="card-heading" onClick={() => openLessonInfo({ lesson: request.lesson, isOpen: true })}>
                                    {request.lesson.heading}
                                </h4>
                                <div>
                                    Is Approved?
                                    <br />
                                    {(request.isApproved == null) &&
                                        <div className='requests-buttons'>
                                            <button>
                                                Approve
                                            </button>
                                            <button>
                                                Reject
                                            </button>
                                        </div>}
                                    {
                                        request.isApproved && <YesIcon />
                                    }
                                    {
                                        (!request.isApproved && request.isApproved != null) && <NoIcon />
                                    }
                                    <br />
                                    Who sent:
                                    <br />
                                    <div>
                                        <a href={'/' + request.sender.humanReadableID} className='link'>{(request.sender.name + " " + request.sender.secondName)}</a>
                                    </div>
                                </div>
                            </div>
                        )
                    })}
                    {outcome.map(request => {
                        return (
                            <div className='card'>
                                {request.id}
                            </div>
                        )
                    })}
                </div>
            </div>
        </div>
    );
};

export default LessonsRequestsHolder;
