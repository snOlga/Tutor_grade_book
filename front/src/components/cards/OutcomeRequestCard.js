import React, { useState } from 'react';
import '../../styles/lessons_requests_style.css'
import { YesIcon, NoIcon } from '../modals/InfoLessonModal';

function OutcomeRequestCard({ request, openLessonInfo, outcome, setOutcome }) {

    function deleteRequest(request) {
        fetch(process.env.REACT_APP_ROOT_PATH + 'lesson-requests/' + request.id, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        })
            .then(response => {
                setOutcome(outcome.filter(req => req.id != request.id))
            })
    }

    return (
        <div className='card'>
            <div className='card-w-bin-header'>
                <h4 className="card-heading" onClick={() => openLessonInfo({ lesson: request.lesson, isOpen: true })}>
                    {request.lesson.heading}
                </h4>
                <a onClick={e => {
                    deleteRequest(request)
                    e.stopPropagation()
                }}
                    className='card-bin'>
                    <svg stroke="currentColor" fill="#EB7C6C" stroke-width="0" viewBox="0 0 24 24" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path fill="none" d="M0 0h24v24H0V0z"></path><path d="M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM8 9h8v10H8V9zm7.5-5-1-1h-5l-1 1H5v2h14V4z"></path></svg>
                </a>
            </div>
            <div className='request-content-holder'>
                <div className='approved-holder'>
                    <div>
                        Is Approved?
                    </div>
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
                </div>
                <div className='approved-holder'>
                    <div>
                        Who got:
                    </div>
                    <div>
                        <a href={'/account/' + request.reciever.humanReadableID} className='link'>{(request.reciever.name + " " + request.reciever.secondName)}</a>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default OutcomeRequestCard;
