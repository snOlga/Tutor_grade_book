import React, { useState, useEffect } from 'react';
import Calendar from "../components/Calendar";
import Header from '../components/Header';
import ChatHolder from '../components/ChatHolder';
import CreateLessonModal from '../components/modals/CreateLessonModal';
import InfoLessonModal from '../components/modals/InfoLessonModal';
import LessonsRequestsHolder from '../components/LessonsRequestsHolder';
import { getRoles, ROLES, getCurrentUserEmail } from '../App';
import StudentSearchUI from '../components/StudentSearchUI';
import { refreshAccessToken } from '../services/auth'

function TutorCalendar() {
    const [isChatOpen, openChat] = useState(false)
    const [isLessonCreationOpen, openLessonCreationModal] = useState(false)
    const [lessonInfoModalState, setLessonInfoModalState] = useState({
        isOpen: false,
        lesson: {}
    })
    const [isLessonsRequestsOpen, openLessonsRequests] = useState(false)
    const [lessons, setLessons] = useState([])
    const isStudent = getRoles().includes(ROLES.STUDENT)
    const [startWeekDate, setStartWeekDate] = useState(new Date())
    const [endWeekDate, setEndWeekDate] = useState(new Date())
    const setWeek = { setStartWeekDate, setEndWeekDate }

    function openLessonInfoModal(state) {
        setLessonInfoModalState({ ...lessonInfoModalState, isOpen: state })
    }

    useEffect(() => {
        loadLessons()
    }, [startWeekDate, endWeekDate])

    function loadLessons() {
        fetch(process.env.REACT_APP_ROOT_PATH + 'lessons/user/' + getCurrentUserEmail(), {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                startDate: startWeekDate.toISOString(),
                endDate: endWeekDate.toISOString()
            })
        })
            .then(response => response.json())
            .then(data => {
                setLessons(data)
            }).catch(() => refreshAccessToken())
    }

    return (
        <>
            <div className="min-h-screen">
                <Header openLessonCreationModal={openLessonCreationModal} openLessonsRequests={openLessonsRequests} openChat={openChat} />
                <div style={{ marginTop: "70px" }}></div>
                {
                    isStudent && <StudentSearchUI setLessons={setLessons} />
                }
                {
                    !isStudent && <div className='filters'></div>
                }
                <Calendar lessons={lessons} setLessonInfoModalState={setLessonInfoModalState} setWeek={setWeek} />
                {
                    isLessonCreationOpen && <CreateLessonModal closeModal={openLessonCreationModal} />
                }
                {
                    lessonInfoModalState.isOpen && <InfoLessonModal closeModal={openLessonInfoModal} currentLesson={lessonInfoModalState.lesson} />
                }
                {
                    isLessonsRequestsOpen && <LessonsRequestsHolder openLessonInfo={setLessonInfoModalState} />
                }
                {
                    isChatOpen && <ChatHolder openChat={openChat} chat={null} />
                }
            </div >
        </>
    );
}

export default TutorCalendar;