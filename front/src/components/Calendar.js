import React from 'react';
import { useEffect, useState } from 'react'
import LessonsCards from './cards/LessonsCards.js';
import '../styles/calendar_style.css'
import { getSubject } from '../App.js'

function Calendar({ setLessonInfoModalState }) {
    const [currentWeek, setCurrentWeek] = useState(0)
    const [days, setDays] = useState([[new Date(), ""]])
    const [weekdays] = useState(['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'])
    const [time] = useState(['08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22'])
    const [lessons, setLessons] = useState([])

    useEffect(() => {
        setDaysOnLoading()
    }, [currentWeek])

    useEffect(() => {
        loadLessons()
    }, [])

    function setDaysOnLoading() {
        const curr = new Date();
        const countedDays = [];
        for (let i = 0; i < 7; i++) {
            const firstDay = new Date(curr);
            firstDay.setDate(curr.getDate() - curr.getDay() + 7 * currentWeek + i);
            const dateNum = firstDay;
            const dateWeekday = [dateNum, weekdays[i]];
            countedDays.push(dateWeekday);
        }
        setDays(countedDays)
    }

    function loadLessons() {
        fetch('http://localhost:18018/lessons/with_user/' + getSubject(), {
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

    function nextWeek() {
        setCurrentWeek(currentWeek + 1)
    }
    function weekBefore() {
        setCurrentWeek(currentWeek - 1)
    }

    return (
        <div>
            <div className="calendar">
                <div className='times'>
                    <div className='line-holder'>
                        <div className='half-of-hour'>
                            <div className='week-buttons'>
                                <button onClick={weekBefore}>{"<"}</button>
                                <button onClick={nextWeek}>{">"}</button>
                            </div>
                        </div>
                    </div>
                    {
                        time.map(time => {
                            return (
                                <div className='line-holder'>
                                    <div className='half-of-hour'>
                                        {time}:00
                                    </div>
                                </div>
                            )
                        })
                    }
                </div>
                <div className='data-days'>
                    {
                        days.map(dayDate => {
                            return (
                                <div className="date">
                                    <div className='day-header'>
                                        <p className="date-num">{dayDate[0].getDate()}</p>
                                        <p className="date-day">{dayDate[1]}</p>
                                    </div>
                                    <div className='day-content'>
                                        <LessonsCards lessons={lessons} currentDate={dayDate[0]} setLessonInfoModalState={setLessonInfoModalState} />
                                        {/* {
                                            time.map(oneTime => {
                                                const topPosition = (oneTime - 7) * 60 + 10
                                                return (
                                                    <button className='create-lesson' style={{ top: topPosition + 'px' }}>click</button>
                                                )
                                            })
                                        } */}
                                    </div>
                                </div>
                            )
                        })
                    }
                </div>
            </div>
        </div>
    );
}

export default Calendar;