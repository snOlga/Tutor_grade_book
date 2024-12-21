import React, { useState } from 'react';
import Calendar from "../components/Calendar";
import Header from '../components/Header';
import ChatHolder from '../components/ChatHolder';
import CreateLessonModal from '../components/CreateLessonModal';

function TutorCalendar() {
    const [isChatOpen, openChat] = useState(false)
    const [isLessonCreationOpen, openLessonCreationModal] = useState(false)
    return (
        <>
            <div className="min-h-screen">
                <Header openChat={openChat} openLessonCreationModal={openLessonCreationModal} />
                <Calendar />
                {
                    isLessonCreationOpen && <CreateLessonModal />
                }
                {/* {
                    isChatOpen && <ChatHolder />
                } */}
            </div >
        </>
    );
}

export default TutorCalendar;