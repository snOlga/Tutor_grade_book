import React, { useState } from 'react';
import '../../../styles/calendar_style.css'
import '../../../styles/card_style.css'
import DeletionModal from './DeletionModal';
import LessonCard from './LessonCard';

function LessonsCards({ lessons, currentDate, setLessonInfoModalState, timeOffset }) {
    const [isDeletionModalOpen, openDeletionModal] = useState(false)
    const [lessonToDelete, setLessonToDelete] = useState(-1)

    return (
        <>
            {
                lessons.map(lesson => {
                    const lessonDate = new Date(lesson.startTime)
                    if (lessonDate.getDate() == currentDate.getDate() &&
                        lessonDate.getMonth() == currentDate.getMonth() &&
                        lessonDate.getFullYear() == currentDate.getFullYear()) {
                        if (isDeletionModalOpen && lessonToDelete == lesson) {
                            return (
                                <DeletionModal valueToDelete={lessonToDelete} closeModal={openDeletionModal} lessonDate={lessonDate} />
                            )
                        }
                        else {
                            return (
                                <LessonCard
                                    lesson={lesson}
                                    lessonDate={lessonDate}
                                    openDeletionModal={openDeletionModal}
                                    setLessonToDelete={setLessonToDelete}
                                    setLessonInfoModalState={setLessonInfoModalState}
                                    timeOffset={timeOffset} />
                            )
                        }
                    }
                })
            }
        </>
    );
};

export default LessonsCards;
