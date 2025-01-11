import React, { useState, useEffect } from 'react';
import Calendar from "../components/Calendar";
import Header from '../components/Header';
import ChatHolder from '../components/ChatHolder';
import CreateLessonModal from '../components/modals/CreateLessonModal';
import InfoLessonModal from '../components/modals/InfoLessonModal';
import LessonsRequestsHolder from '../components/LessonsRequestsHolder';
import { getRoles, ROLES, getCurrentUserEmail } from '../App';
import StudentSearchUI from '../components/StudentSearchUI';

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

    function openLessonInfoModal(state) {
        setLessonInfoModalState({ ...lessonInfoModalState, isOpen: state })
    }

    useEffect(() => {
        loadLessons()
    }, [])

    function loadLessons() {
        fetch('http://localhost:18018/lessons/with_user/' + getCurrentUserEmail(), {
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
                <Calendar lessons={lessons} setLessonInfoModalState={setLessonInfoModalState} />
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
                    isChatOpen && <ChatHolder />
                }
            </div >
        </>
    );
}

export default TutorCalendar;