## Task 4: Audio To Text. Make a request to LLM to transcribe audion

Documentation: https://platform.openai.com/docs/guides/speech-to-text

## Pre Steps:
🔑 Add to the project Open-AI API key https://platform.openai.com/settings/organization/api-keys (It is not Free, you
   need to pay 1-10$ for subscription)


### Steps
1. Create request to LLM
2. Call LLM `https://api.openai.com/v1/audio/transcriptions`. 
3. Get response
4. Validate with tests `AudioToTextTest.`. 
5. **PAY ATTENTION THAT TESTS PROVIDE ONLY END-TO-END SCENARIO AND CHECK IF AUDIO `codeus_audio.mp3` WAS TRANSCRIPTED**