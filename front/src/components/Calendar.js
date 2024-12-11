import React from 'react';
import { useEffect, useState } from 'react'
import '../styles/calendar_style.css'

const mockJson = [
    {
        "id": "1",
        "students_participator": ["Kyle", "Mike"],
        "tutors_participator": ["Ms March", "Mr Brown"],
        "timestamp": "November 22, 2024 11:00:00",
        "duration": "1",
        "heading": "SomeCoolLesson",
        "description": "you will be interested at this lesson!"
    },
    {
        "id": "2",
        "students_participator": ["Kyle", "Mike"],
        "tutors_participator": ["Ms March", "Mr Brown"],
        "timestamp": "November 20, 2024 10:00:00",
        "duration": "1",
        "heading": "Foo",
        "description": "you will be interested at this lesson!"
    },
    {
        "id": "3",
        "students_participator": ["Kyle", "Mike"],
        "tutors_participator": ["Ms March", "Mr Brown"],
        "timestamp": "November 20, 2024 11:00:00",
        "duration": "1",
        "heading": "Foo",
        "description": "you will be interested at this lesson!"
    }
]

const Calendar = () => {
    const [currentWeek, setCurrentWeek] = useState(0)
    const [days, setDays] = useState([[new Date(), ""]])
    const [weekdays] = useState(['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'])
    const [time] = useState(['08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22'])

    useEffect(() => {
        const curr = new Date();
        const countedDays = [];
        for (let i = 0; i < 7; i++) {
            const firstDay = new Date(curr);
            firstDay.setDate(curr.getDate() - curr.getDay() + 7 * currentWeek + i);
            const dateNum = firstDay;
            const dateWeekday = [dateNum, weekdays[i]];
            countedDays.push(dateWeekday);
        }
        setDays(countedDays);
    }, [currentWeek]);

    function nextWeek() {
        setCurrentWeek(currentWeek + 1)
    }
    function weekBefore() {
        setCurrentWeek(currentWeek - 1)
    }

    return (
        <div>
            <div>
                <button onClick={weekBefore}>{"<"}</button>
                <button onClick={nextWeek}>{">"}</button>
            </div>
            <div class="calendar">
                <div className='times'>
                    <div className='line-holder'>
                        <div className='half-of-hour'>
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
                                <div class="date">
                                    <div className='day-header'>
                                        <p class="date-num">{dayDate[0].getDate()}</p>
                                        <p class="date-day">{dayDate[1]}</p>
                                    </div>
                                    <div className='day-content'>
                                        {
                                            mockJson.map(lesson => {
                                                const lessonDate = new Date(lesson.timestamp)
                                                const currentDate = dayDate[0]
                                                if (lessonDate.getDate() == currentDate.getDate() &&
                                                    lessonDate.getMonth() == currentDate.getMonth() &&
                                                    lessonDate.getFullYear() == currentDate.getFullYear()) {
                                                    const topPositionLesson = (lessonDate.getHours() - 7) * 60 + lessonDate.getMinutes() + 10;
                                                    return (
                                                        <div className="lesson" style={{ top: topPositionLesson + 'px' }}>
                                                            <div className="lesson-time">
                                                                { lessonDate.toLocaleTimeString().substring(0, 5) + " - " + (new Date(lessonDate.getTime() + lesson.duration*60*60*1000)).toLocaleTimeString().substring(0, 5) }
                                                            </div>
                                                            <div className="lesson-details">
                                                                <h4 className="lesson-heading">
                                                                    {lesson.heading}
                                                                </h4>
                                                                <p className="lesson-tutors">
                                                                    {lesson.tutors_participator.map(tutor => {
                                                                        return (
                                                                            <div>
                                                                                {tutor}
                                                                            </div>
                                                                        )
                                                                    })}
                                                                </p>
                                                            </div>
                                                        </div>
                                                    )
                                                }
                                            })
                                        }
                                        {
                                            time.map(oneTime => {
                                                const topPosition = (oneTime - 7) * 60 + 10
                                                return (
                                                    <button className='create-lesson' style={{ top: topPosition + 'px' }}>click</button>
                                                )
                                            })
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