import React, { useState } from 'react';
import '../../styles/lessons_requests_style.css'
import { YesIcon, NoIcon } from '../modals/InfoLessonModal';

function IncomeRequestCard({ request, openLessonInfo, income, setIncome }) {

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
                setIncome(income.map(request => request.id == data.id ? data : request))
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
                setIncome(income.map(request => request.id == data.id ? data : request))
            })
    }

    return (
        <div className='card' onClick={() => openLessonInfo({ lesson: request.lesson, isOpen: true })}>
            <h4 className="card-heading" >
                {request.lesson.heading}
            </h4>
            <div className='request-content-holder'>
                <div className='approved-holder'>
                    <div>
                        Is Approved?
                    </div>
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
                </div>
                <div className='approved-holder'>
                    <div>
                        Who sent:
                    </div>
                    <div>
                        <a href={'/account/' + request.sender.humanReadableID} className='link'>{(request.sender.name + " " + request.sender.secondName)}</a>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default IncomeRequestCard;
