## Task 6: Audio To Audio. Make a request to LLM to create audio response from audio request

Documentation: https://platform.openai.com/docs/guides/audio?example=audio-in

<details> 
<summary>Request sample</summary>

```markdown
curl "https://api.openai.com/v1/chat/completions" \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer $OPENAI_API_KEY" \
    -d '{
      "model": "gpt-4o-audio-preview",
      "modalities": ["text", "audio"],
      "audio": { "voice": "alloy", "format": "wav" },
      "messages": [
        {
          "role": "user",
          "content": [
            { "type": "text", "text": "What is in this recording?" },
            {
              "type": "input_audio",
              "input_audio": {
                "data": "<base64 bytes here>",
                "format": "wav"
              }
            }
          ]
        }
      ]
    }'
```
</details>

## Pre Steps:
🔑 Add to the project Open-AI API key https://platform.openai.com/settings/organization/api-keys (It is not Free, you
   need to pay 1-10$ for subscription)


### Steps
1. Create request to LLM
2. Call LLM `https://api.openai.com/v1/chat/completions`. Don't forget that content type is `application/json`
3. Get response as byte array output and save it
4. Validate with tests `task_6.AudioToAudioTest`. 
5. **PAY ATTENTION THAT TESTS PROVIDE ONLY END-TO-END SCENARIO AND CHECK IF THE FILE CAN BE CREATED FROM CONTENT**