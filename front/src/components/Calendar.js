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
        "timestamp": "November 20, 2024 11:00:00",
        "duration": "1",
        "heading": "SomeCoolLesson",
        "description": "you will be interested at this lesson!"
    }
]

const Calendar = () => {
    const [currentWeek, setCurrentWeek] = useState(0)
    const [days, setDays] = useState([[new Date(), ""]])
    const [weekdays] = useState(['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'])
    const [time] = useState(['08 AM', '09 AM', '10 AM', '11 AM', '12 PM', '1 PM', '2 PM', '3 PM', '4 PM'])

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
                <div class="timeline">
                    <div class="spacer"></div>
                    {
                        time.map((time) =>
                        (
                            <div class="time-marker">{time}</div>
                        ))
                    }
                </div>
                <div class="days">
                    {
                        days.map(dayDate => {
                            const lessonsForDay = mockJson.filter(lesson => {
                                const lessonDate = new Date(lesson.timestamp);
                                return lessonDate.getFullYear() == dayDate[0].getFullYear() &&
                                    lessonDate.getMonth() == dayDate[0].getMonth() &&
                                    lessonDate.getDate() == dayDate[0].getDate();
                            });

                            return (
                                <div>
                                    <div class="date">
                                        <p class="date-num">{dayDate[0].getDate()}</p>
                                        <p class="date-day">{dayDate[1]}</p>
                                    </div>
                                    <div class="events">
                                        {
                                            lessonsForDay.map(lesson => (
                                                <div class="event start-1 end-4 ent-law">
                                                    <p class="title">{lesson.heading}</p>
                                                    <p class="time">{lesson.timestamp}</p>
                                                    <p class="description">{lesson.description}</p>
                                                </div>
                                            ))
                                        }
                                    </div>
                                </div>
                            );
                        })
                    }
                </div>
            </div>
        </div>
    );
}

export default Calendar;