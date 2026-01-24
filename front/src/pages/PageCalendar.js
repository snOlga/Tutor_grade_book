import React, { useState } from 'react';
import Calendar from "../features/calendar/Calendar";
import Header from '../shared/Header';
import ChatHolder from '../features/chat/ChatHolder';
import CreateLessonModal from '../features/lessons/modals/CreateLessonModal';
import InfoLessonModal from '../features/lessons/modals/InfoLessonModal';
import LessonsRequestsHolder from '../features/lessons/LessonsRequestsHolder';
import { getRoles, ROLES } from '../utils/auth';
import StudentSearchUI from '../features/search/StudentSearchUI';
import { useLessons } from '../hooks/useLessons';

function TutorCalendar() {
    const [isChatOpen, openChat] = useState(false)
    const [isLessonCreationOpen, openLessonCreationModal] = useState(false)
    const [lessonInfoModalState, setLessonInfoModalState] = useState({
        isOpen: false,
        lesson: {}
    })
    const [isLessonsRequestsOpen, openLessonsRequests] = useState(false)
    const isStudent = getRoles().includes(ROLES.STUDENT)
    const [startWeekDate, setStartWeekDate] = useState(new Date())
    const [endWeekDate, setEndWeekDate] = useState(new Date())
    const setWeek = { setStartWeekDate, setEndWeekDate }
    const { lessons, setLessons } = useLessons(startWeekDate, endWeekDate)

    function openLessonInfoModal(state) {
        setLessonInfoModalState({ ...lessonInfoModalState, isOpen: state })
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