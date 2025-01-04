import React, { useState } from 'react';
import Calendar from "../components/Calendar";
import Header from '../components/Header';
import ChatHolder from '../components/ChatHolder';
import CreateLessonModal from '../components/modals/CreateLessonModal';
import InfoLessonModal from '../components/modals/InfoLessonModal';
import NotificationsHolder from '../components/NotificationsHolder';

function TutorCalendar() {
    const [isChatOpen, openChat] = useState(false)
    const [isLessonCreationOpen, openLessonCreationModal] = useState(false)
    const [lessonInfoModalState, setLessonInfoModalState] = useState({
        isOpen: false,
        lesson: {}
    })
    const [isNotificationsOpen, openNotifications] = useState(false)

    function openLessonInfoModal(state) {
        setLessonInfoModalState({ ...lessonInfoModalState, isOpen: state })
    }

    return (
        <>
            <div className="min-h-screen">
                <Header openLessonCreationModal={openLessonCreationModal} openNotifications={openNotifications} />
                <Calendar setLessonInfoModalState={setLessonInfoModalState} />
                {
                    isLessonCreationOpen && <CreateLessonModal closeModal={openLessonCreationModal} />
                }
                {
                    lessonInfoModalState.isOpen && <InfoLessonModal closeModal={openLessonInfoModal} currentLesson={lessonInfoModalState.lesson} />
                }
                {
                    isNotificationsOpen && <NotificationsHolder />
                }
                {/* {
                    isChatOpen && <ChatHolder />
                } */}
            </div >
        </>
    );
}

export default TutorCalendar;