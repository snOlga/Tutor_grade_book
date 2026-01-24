import React from 'react';
import { useEffect, useState } from 'react'
import LessonsCards from '../lessons/cards/LessonsCards';
import '../../styles/calendar_style.css'
import { getCurrentUserEmail } from '../../utils/auth'

function Calendar({ lessons, setLessonInfoModalState, setWeek }) {
    const [currentWeek, setCurrentWeek] = useState(0)
    const [days, setDays] = useState([[new Date(), ""]])
    const [weekdays] = useState(['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'])
    const [time, setTime] = useState([...Array(25).keys()])
    const [isMorningFolded, foldMorning] = useState(false)
    const [timeOffset, setTimeOffset] = useState(1)
    const [visibleLessons, setVisibleLessons] = useState([...lessons])

    useEffect(() => {
        setDaysOnLoading()
    }, [currentWeek])

    useEffect(() => {
        foldingMorning()
    }, [isMorningFolded])

    useEffect(() => {
        setVisibleLessons(lessons)
    }, [lessons])

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
        setWeek.setStartWeekDate(countedDays[0][0])
        setWeek.setEndWeekDate(countedDays[countedDays.length - 1][0])
    }

    function nextWeek() {
        setCurrentWeek(currentWeek + 1)
    }
    function weekBefore() {
        setCurrentWeek(currentWeek - 1)
    }

    function foldingMorning() {
        if (isMorningFolded) {
            setTime([...Array(18).keys()].map(t => t + 7))
            setTimeOffset(-6)
            setVisibleLessons(lessons.filter(lesson => new Date(lesson.startTime).getHours() >= 7))
        }
        else {
            setTime([...Array(25).keys()])
            setTimeOffset(1)
            setVisibleLessons(lessons)
        }
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
                                    {
                                        (time == 7) &&
                                        <div className='week-buttons'>
                                            <button onClick={() => foldMorning(!isMorningFolded)}>
                                                {
                                                    !isMorningFolded &&
                                                    <div>
                                                        ↑
                                                    </div>
                                                }
                                                {
                                                    isMorningFolded &&
                                                    <div>
                                                        ↓
                                                    </div>
                                                }
                                            </button>
                                        </div>
                                    }
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
                                        {
                                            visibleLessons != [] &&
                                            <LessonsCards lessons={visibleLessons} currentDate={dayDate[0]} setLessonInfoModalState={setLessonInfoModalState} timeOffset={timeOffset} />
                                        }
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