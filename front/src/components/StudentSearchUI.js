import React, { useState } from 'react';
import SubjectFilters from './SubjectFilters';
import TutorSearch from './TutorSearch';

function StudentSearchUI({ setLessons }) {

    return (
        <div className="calendar no-background">
            <div className='account-content'>
            </div>
            <div className='data-holder'>
                <div className='one-line-data-holder'>
                    <div>
                        <SubjectFilters setLessons={setLessons} />
                    </div>
                    <div>
                        <TutorSearch setLessons={setLessons} />
                    </div>
                </div>
            </div>
        </div>
    );
};

export default StudentSearchUI;
