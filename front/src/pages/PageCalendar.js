import React, { useState } from 'react';
import Calendar from "../components/Calendar";
import Header from '../components/Header';
import ChatHolder from '../components/ChatHolder';
import CreateLessonModal from '../components/modals/CreateLessonModal';
import InfoLessonModal from '../components/modals/InfoLessonModal';
import LessonsRequestsHolder from '../components/LessonsRequestsHolder';
import SubjectFilters from '../components/SubjectFilters';

function TutorCalendar() {
    const [isChatOpen, openChat] = useState(false)
    const [isLessonCreationOpen, openLessonCreationModal] = useState(false)
    const [lessonInfoModalState, setLessonInfoModalState] = useState({
        isOpen: false,
        lesson: {}
    })
    const [isLessonsRequestsOpen, openLessonsRequests] = useState(false)

    function openLessonInfoModal(state) {
        setLessonInfoModalState({ ...lessonInfoModalState, isOpen: state })
    }

    return (
        <>
            <div className="min-h-screen">
                <Header openLessonCreationModal={openLessonCreationModal} openLessonsRequests={openLessonsRequests} />
                <SubjectFilters />
                <Calendar setLessonInfoModalState={setLessonInfoModalState} />
                {
                    isLessonCreationOpen && <CreateLessonModal closeModal={openLessonCreationModal} />
                }
                {
                    lessonInfoModalState.isOpen && <InfoLessonModal closeModal={openLessonInfoModal} currentLesson={lessonInfoModalState.lesson} />
                }
                {
                    isLessonsRequestsOpen && <LessonsRequestsHolder openLessonInfo={setLessonInfoModalState} />
                }
                {/* {
                    isChatOpen && <ChatHolder />
                } */}
            </div >
        </>
    );
}

export default TutorCalendar;