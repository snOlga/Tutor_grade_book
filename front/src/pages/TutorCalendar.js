import React, { useState } from 'react';
import Calendar from "../components/Calendar";
import Header from '../components/Header';
import ChatHolder from '../components/ChatHolder';
import CreateLessonModal from '../components/modals/CreateLessonModal';
import InfoLessonModal from '../components/modals/InfoLessonModal';

function TutorCalendar() {
    const [isChatOpen, openChat] = useState(false)
    const [isLessonCreationOpen, openLessonCreationModal] = useState(false)
    return (
        <>
            <div className="min-h-screen">
                <Header openChat={openChat} openLessonCreationModal={openLessonCreationModal} />
                <Calendar />
                {
                    isLessonCreationOpen && <CreateLessonModal closeModal={openLessonCreationModal} />
                }
                {/* {
                    isChatOpen && <ChatHolder />
                } */}
            </div >
        </>
    );
}

export default TutorCalendar;