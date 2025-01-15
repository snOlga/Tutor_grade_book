import React, { useEffect, useState } from 'react';
import '../styles/lessons_requests_style.css'
import { getCurrentUserEmail } from '../App';
import { YesIcon, NoIcon } from './modals/InfoLessonModal';
import IncomeRequestCard from './cards/IncomeRequestCard';
import OutcomeRequestCard from './cards/OutcomeRequestCard';

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
        fetch(process.env.REACT_APP_ROOT_PATH + 'lesson-requests/outcome/' + getCurrentUserEmail(), {
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
        fetch(process.env.REACT_APP_ROOT_PATH + 'lesson-requests/income/' + getCurrentUserEmail(), {
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

    return (
        <div className='all-window-2' onClick={() => window.location.reload()}>
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
                                    <IncomeRequestCard request={request} openLessonInfo={openLessonInfo} income={income} setIncome={setIncome} />
                                )
                            })}
                        </div>
                        <div className='requests-cards outcome-position'>
                            {showOutcome && outcome.map(request => {
                                return (
                                    <OutcomeRequestCard request={request} openLessonInfo={openLessonInfo} outcome={outcome} setOutcome={setOutcome} />
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
