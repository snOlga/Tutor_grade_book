import React, { useEffect, useState } from 'react';
import '../styles/lessons_requests_style.css'
import { getCurrentUserEmail } from '../App';
import { YesIcon, NoIcon } from './modals/InfoLessonModal';

function LessonsRequestsHolder({ openLessonInfo }) {
    const [showIncome, setShowIncome] = useState(true)
    const [showOutcome, setShowOutcome] = useState(false)
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
                setIncome(data)
            })
    }

    function approveRequest(request) {
        fetch("http://localhost:18018/lesson_requests/update_approvement/" + request.id, {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                ...request, isApproved: true
            })
        })
            .then(response => response.json())
            .then(data => {
                console.log(data)
            })
    }

    function rejectRequest(request) {
        fetch("http://localhost:18018/lesson_requests/update_approvement/" + request.id, {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                ...request, isApproved: false
            })
        })
            .then(response => response.json())
            .then(data => {
                setOutcome(outcome.map(request => request.id == data ? data : request))
            })
    }

    function deleteRequest(request) {
        fetch("http://localhost:18018/lesson_requests/" + request.id, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        })
            .then(response => {
                fetchOutcomeRequests()
                fetchIncomeRequests()
            })
    }

    return (
        <div className='all-window' onClick={() => window.location.reload()}>
            <div className='lessons-requests-holder' onClick={e => e.stopPropagation()}>
                <div className='lessons-requests-content'>
                    <div className='requests-buttons'>
                        <button onClick={() => {
                            setShowIncome(!showIncome)
                            setShowOutcome(!showOutcome)
                            fetchIncomeRequests()
                        }}
                            disabled={showIncome}>Income Requests</button>
                        <button onClick={() => {
                            setShowIncome(!showIncome)
                            setShowOutcome(!showOutcome)
                            fetchOutcomeRequests()
                        }} className='outcome-position'
                            disabled={showOutcome}>Outcome Requests</button>
                    </div>
                    <div>
                        <div className='requests-cards'>
                            {showIncome && income.map(request => {
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
                                                    <button onClick={() => approveRequest(request)}>
                                                        Approve
                                                    </button>
                                                    <button onClick={() => rejectRequest(request)}>
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
                                                <a href={'/account/' + request.sender.humanReadableID} className='link'>{(request.sender.name + " " + request.sender.secondName)}</a>
                                            </div>
                                        </div>
                                    </div>
                                )
                            })}
                        </div>
                        <div className='requests-cards outcome-position'>
                            {showOutcome && outcome.map(request => {
                                return (
                                    <div className='card'>
                                        <div className='card-w-bin-header'>
                                            <h4 className="card-heading" onClick={() => openLessonInfo({ lesson: request.lesson, isOpen: true })}>
                                                {request.lesson.heading}
                                            </h4>
                                            <a onClick={() => {
                                                deleteRequest(request)
                                            }} >
                                                <svg stroke="currentColor" fill="#EB7C6C" stroke-width="0" viewBox="0 0 24 24" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path fill="none" d="M0 0h24v24H0V0z"></path><path d="M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM8 9h8v10H8V9zm7.5-5-1-1h-5l-1 1H5v2h14V4z"></path></svg>
                                            </a>
                                        </div>
                                        <div>
                                            Is Approved?
                                            <br />
                                            {(request.isApproved == null) &&
                                                <div>
                                                    Not seen yet...
                                                </div>}
                                            {
                                                request.isApproved && <YesIcon />
                                            }
                                            {
                                                (!request.isApproved && request.isApproved != null) && <NoIcon />
                                            }
                                            <br />
                                            Who got:
                                            <br />
                                            <div>
                                                <a href={'/account/' + request.reciever.humanReadableID} className='link'>{(request.reciever.name + " " + request.reciever.secondName)}</a>
                                            </div>
                                        </div>
                                    </div>
                                )
                            })}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default LessonsRequestsHolder;
