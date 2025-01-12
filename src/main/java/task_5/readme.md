## Task 5: Text To Audio. Make a request to LLM to create audio from text

Documentation: https://platform.openai.com/docs/guides/text-to-speech

<details> 
<summary>Request sample to generate picture</summary>

```markdown
curl https://api.openai.com/v1/audio/speech \
  -H "Authorization: Bearer $OPENAI_API_KEY" \
  -H "Content-Type: application/json" \
  -d '{
    "model": "tts-1",
    "input": "Today is a wonderful day to build something people love!",
    "voice": "alloy"
  }' \
  --output speech.mp3
```
</details>

## Pre Steps:
🔑 Add to the project Open-AI API key https://platform.openai.com/settings/organization/api-keys (It is not Free, you
   need to pay 1-10$ for subscription)


### Steps
1. Create request to LLM
2. Call LLM `https://api.openai.com/v1/chat/completions`. Don't forget that content type is `application/json`
3. Get response as byte array output and save it
4. Validate with tests `task_5.TextToAudioTest`. 
5. **PAY ATTENTION THAT TESTS PROVIDE ONLY END-TO-END SCENARIO AND CHECK IF THE FILE CAN BE CREATED FROM CONTENT**